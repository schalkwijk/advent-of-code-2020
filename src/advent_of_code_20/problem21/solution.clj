(ns advent-of-code-20.problem21.solution
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :refer :all]))

(defn- make-recipe [input]
  (let [[ingredients allergens] (rest (re-matches #"(.*) \(contains (.*)\)" input))]
    {:ingredients (set (str/split ingredients #" ")) :allergens (set (str/split (str/replace allergens #" " "") #","))}))

(defn- invalid-ingredient-and-allergen-pairing? [recipes [ingredient allergen]]
  (let [target-recipes (filter #(contains? (:allergens %) allergen) recipes)]
    (some #(not (contains? (:ingredients %) ingredient)) target-recipes)))

(defn part1 [input]
  (let [recipes (map make-recipe input)
        all-ingredients (set (mapcat :ingredients recipes))
        all-allergens (set (mapcat :allergens recipes))
        count-of-ingredients-per-recipe (frequencies (mapcat :ingredients recipes))]
    (->> all-allergens
         (cartesian-product all-ingredients)
         (filter #(invalid-ingredient-and-allergen-pairing? recipes %))
         (reduce #(update %1 (first %2) (fnil conj '()) (second %2)) {})
         (filter #(= (count all-allergens) (count (second %))))
         (map first)
         (map #(get count-of-ingredients-per-recipe %))
         (apply +))))

(defn part2 [input] 10)
