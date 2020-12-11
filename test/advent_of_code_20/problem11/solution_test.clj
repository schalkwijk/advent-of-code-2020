(ns advent-of-code-20.problem1.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem11.solution :refer :all]))

(deftest part1-test
  (testing "occupied seat count"
    (let [input (str/split (slurp "test/advent_of_code_20/problem11/input.txt") #"\n")]
      (is (= 20 (part1 input 2)))
      (is (= 51 (part1 input 3)))
      (is (= 37 (part1 input 10))))))
