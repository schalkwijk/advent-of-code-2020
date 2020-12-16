(ns advent-of-code-20.problem16.solution
  (:require [clojure.string :as str]))

(defn- to-int [string] (Integer/parseInt string))

(defn- create-range [range]
  (map to-int (str/split range #"-")))

(defn- extract-categories [categories]
  (mapcat #(map create-range %) (map #(rest (re-matches #".*: (.*-.*) or (.*-.*)" %)) (str/split categories #"\n"))))

(defn- extract-ticket [ticket]
  (map to-int (str/split ticket #",")))

(defn- extract-nearby-tickets [tickets]
  (map extract-ticket (rest tickets)))

(defn- process-input [input]
  {:categories (extract-categories (first input))
   :ticket (extract-ticket (last (str/split (second input) #"\n")))
   :nearby (extract-nearby-tickets (rest (str/split (last input) #"\n")))})

(defn- valid-value? [value [lower upper]]
  (and (>= value lower) (<= value upper)))

(defn- invalid-value? [ranges value]
  (not (some #(valid-value? value %) ranges)))

(defn part1 [input]
  (let [state (process-input input)]
    (apply + (filter #(invalid-value? (:categories state) %) (flatten (:nearby state))))))

(defn part2 [input] 10)
