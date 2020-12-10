(ns advent-of-code-20.problem9.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem9.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem9/input.txt") #"\n")]
      (is (= 127 (part1 input 5))))))

(deftest part2-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem9/input.txt") #"\n")]
      (is (= 62 (part2 input 5))))))
