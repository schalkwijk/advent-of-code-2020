(ns advent-of-code-20.problem21.solution
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :refer :all]))

(defn- make-recipe [input]
  (let [[ingredients allergens] (rest (re-matches #"(.*) \(contains (.*)\)" input))]
    {:ingredients (set (str/split ingredients #" ")) :allergens (set (str/split (str/replace allergens #" " "") #","))}))

(defn- invalid-ingredient-and-allergen-pairing? [recipes [ingredient allergen]]
  (let [target-recipes (filter #(contains? (:allergens %) allergen) recipes)]
    (some #(not (contains? (:ingredients %) ingredient)) target-recipes)))

(defn find-inert-ingredients [recipes]
  (let [all-ingredients (set (mapcat :ingredients recipes))
        all-allergens (set (mapcat :allergens recipes))
        count-of-ingredients-per-recipe (frequencies (mapcat :ingredients recipes))]
    (->> all-allergens
         (cartesian-product all-ingredients)
         (filter #(invalid-ingredient-and-allergen-pairing? recipes %))
         (reduce #(update %1 (first %2) (fnil conj '()) (second %2)) {})
         (filter #(= (count all-allergens) (count (second %))))
         (map first))))

(defn part1 [input]
  (let [recipes (map make-recipe input)
        count-of-ingredients-per-recipe (frequencies (mapcat :ingredients recipes))
        inert-ingredients (find-inert-ingredients recipes)]
    (->> inert-ingredients
         (map #(get count-of-ingredients-per-recipe %))
         (apply +))))

(defn- remove-allergens-from-recipe [allergens recipe]
  (update recipe :allergens (fn [i] (set (filter #(not (contains? allergens %)) i)))))

(defn- remove-ingredients-from-recipe [ingredients recipe]
  (update recipe :ingredients (fn [i] (set (filter #(not (contains? ingredients %)) i)))))

(defn- remove-ingredients-from-recipes [recipes ingredients]
  (map (partial remove-ingredients-from-recipe (set ingredients)) recipes))

(defn- remove-allergens-from-recipes [recipes allergens]
  (map (partial remove-allergens-from-recipe (set allergens)) recipes))

(defn- remove-pairing [{:keys [recipes pairings] :as state} [ingredient allergen]]
  (-> state
      (update :recipes remove-ingredients-from-recipes [ingredient])
      (update :recipes remove-allergens-from-recipes [allergen])
      (update :pairings conj [ingredient allergen])))

(defn find-valid-allergens [{:keys [recipes pairings] :as state}]
  (let [all-ingredients (set (mapcat :ingredients recipes))
        all-allergens (set (mapcat :allergens recipes))
        all-pairings (cartesian-product all-ingredients all-allergens)
        valid-pairings (filter #(not (invalid-ingredient-and-allergen-pairing? recipes %)) all-pairings)]
    (if (= (count all-pairings) 0) [state] (mapcat #(find-valid-allergens (remove-pairing state %)) valid-pairings))))

(defn part2 [input]
  (let [recipes (map make-recipe input)
        inert-ingredients (find-inert-ingredients recipes)
        updated-recipes (remove-ingredients-from-recipes recipes inert-ingredients)]
    (->> {:recipes updated-recipes :pairings []}
         find-valid-allergens
         (map :pairings)
         first
         (sort-by second)
         (map first)
         (str/join ","))))
