(ns advent-of-code-20.problem19.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem19.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem19/input.txt") #"\n\n")]
      (is (= 2 (part1 input))))))
