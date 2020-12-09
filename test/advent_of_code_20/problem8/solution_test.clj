(ns advent-of-code-20.problem1.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem8.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem8/input.txt") #"\n")]
      (is (= 5 (part1 input))))))

(deftest part2-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem8/input.txt") #"\n")]
      (is (= 8 (part2 input))))))
