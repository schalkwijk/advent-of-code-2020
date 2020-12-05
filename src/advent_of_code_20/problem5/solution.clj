(ns advent-of-code-20.problem5.solution
  (:require [clojure.math.combinatorics :refer :all]
            [clojure.string :as str]))

(defn- normalize [direction]
  (case direction
    "F" :bottom
    "B" :top
    "R" :top
    "L" :bottom))

(defn split [direction [lower upper]]
  (let [midpoint (int (/ (+ lower upper) 2))]
    (if (= direction :bottom) [lower midpoint] [(inc midpoint) upper])))

(defn find-target [directions [lower upper]]
  (let [normalized-directions (map normalize (str/split directions #""))]
    (first (first (filter #(apply = %) (reductions (fn [bounds direction] (split direction bounds)) [lower upper] normalized-directions))))))

(defn find-row [directions]
  (find-target directions [0 127]))

(defn find-column [directions]
  (find-target directions [0 7]))

(defn find-seat [directions]
  (+ (find-column (subs directions 7 10)) (* 8 (find-row (subs directions 0 7)))))

(defn part1 [passes]
  (apply max (map find-seat passes)))

(defn- missing-seat [seats [first-id second-id]]
  (let [difference (- first-id second-id)]
    (and (= 2 (Math/abs difference)) (not (contains? (set seats) (+ (/ difference 2) second-id))))))

(defn part2 [passes]
  (let [seats (map find-seat passes)]
    (inc (first (sort (first (filter #(missing-seat seats %) (combinations seats 2))))))))
