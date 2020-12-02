(ns advent-of-code-20.problem1.solution
  (:require [clojure.math.combinatorics :refer :all]))

(defn- sum-to-target? [target numbers]
  (= target (apply + numbers)))

(defn part1 [numbers]
  (apply * (first (filter (partial sum-to-target? 2020) (combinations numbers 2)))))

(defn part2 [numbers]
  (apply * (first (filter (partial sum-to-target? 2020) (combinations numbers 3)))))
