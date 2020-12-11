(ns advent-of-code-20.problem1.solution
  (:require [clojure.math.combinatorics :refer :all]))

(defn to-int [string]
  (Integer/parseInt string))

(defn- sum-to-target? [target numbers]
  (= target (apply + numbers)))

(defn part1 [numbers]
  (apply * (first (filter (partial sum-to-target? 2020) (combinations (map to-int numbers) 2)))))

(defn part2 [numbers]
  (apply * (first (filter (partial sum-to-target? 2020) (combinations (map to-int numbers) 3)))))
