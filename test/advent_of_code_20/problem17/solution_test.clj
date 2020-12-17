(ns advent-of-code-20.problem17.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem17.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem17/input.txt") #"\n")]
      (is (= 112 (part1 input))))))
