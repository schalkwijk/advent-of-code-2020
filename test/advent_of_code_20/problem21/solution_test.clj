(ns advent-of-code-20.problem21.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem21.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem21/input.txt") #"\n")]
      (is (= 5 (part1 input))))))
