(ns advent-of-code-20.problem15.solution
  (:require [clojure.string :as str]))

(defn- to-int [string]
  (Integer/parseInt string))

(defn compute-next-number [numbers]
  (let [target-number (last numbers)
        index (inc (.lastIndexOf (subvec numbers 0 (dec (count numbers))) target-number))]
    (if (= index 0) (conj numbers 0) (conj numbers (- (count numbers) index)))))

(defn part1 [input]
  (let [starting-numbers (vec (map to-int (str/split (first input) #",")))]
    (last (nth (reductions (fn [state _] (compute-next-number state)) starting-numbers (range)) (- 2020 (count starting-numbers))))))

(defn part2 [input] 10)
