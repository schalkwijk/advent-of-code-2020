(ns advent-of-code-20.problem6.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem6.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem6/input.txt") #"\n\n")]
      (is (= 11 (part1 input))))))

(deftest part2-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem6/input.txt") #"\n\n")]
      (is (= 6 (part1 input))))))
