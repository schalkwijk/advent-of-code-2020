(ns advent-of-code-20.problem16.solution
  (:require [clojure.string :as str]))

(defn- to-int [string] (Integer/parseInt string))

(defn- create-range [range]
  (map to-int (str/split range #"-")))

(defn- create-category [[category & rest]]
  {:name category :ranges (map create-range rest)})

(defn- extract-categories [categories]
  (map create-category (map #(rest (re-matches #"(.*): (.*-.*) or (.*-.*)" %)) (str/split categories #"\n"))))

(defn- extract-ticket [ticket]
  (map to-int (str/split ticket #",")))

(defn- extract-nearby-tickets [tickets]
  (map extract-ticket tickets))

(defn process-input [input]
  {:categories (extract-categories (first input))
   :ticket (extract-ticket (last (str/split (second input) #"\n")))
   :nearby (extract-nearby-tickets (rest (str/split (last input) #"\n")))})

(defn- valid-value? [value [lower upper]]
  (and (>= value lower) (<= value upper)))

(defn- invalid-value? [ranges value]
  (not (some #(valid-value? value %) ranges)))

(defn part1 [input]
  (let [state (process-input input)]
    (apply + (filter #(invalid-value? (mapcat :ranges (:categories state)) %) (flatten (:nearby state))))))

(defn pluck-category [{:keys [positioned remaining] :as state} _]
  (let [[index [category]] (->> remaining
                                (map-indexed #(identity [%1 (filter (fn [category] (not (contains? (set (keys positioned)) category))) %2)]))
                  (filter #(= 1 (count (last %))))
                  first)]
    (assoc-in state [:positioned category] index)))

(defn disambiguate [categories]
  (reduce pluck-category {:positioned {} :remaining categories} (range 0 (count categories))))

(defn- valid-within-some-range? [ranges value]
  (some #(valid-value? value %) ranges))

(defn- valid-category? [values {:keys [ranges]}]
  (every? #(valid-within-some-range? ranges %) values))

(defn- find-category [categories values]
  (map :name (filter #(valid-category? values %) categories)))

(defn- valid-ticket? [categories values]
  (every? #(some (fn [category] (valid-within-some-range? (:ranges category) %)) categories) values))

(defn- filter-out-invalid-tickets [{:keys [categories nearby] :as state}]
  (assoc state :nearby (filter #(valid-ticket? categories %) nearby)))

(defn categorize-seats [state]
  (->> state
       filter-out-invalid-tickets
       :nearby
       (apply map vector)
       (map #(find-category (:categories state) %))
       disambiguate
       :positioned
       vec))

(defn part2 [input]
  (let [state (process-input input)]
    (->> state
         categorize-seats
         (filter #(re-matches #"departure.*" (first %)))
         (map second)
         (map #(nth (:ticket state) %))
         (apply *))))
