(ns advent-of-code-20.problem25.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem25.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem25/input.txt") #"\n")]
      (is (= 14897079 (part1 input))))))
