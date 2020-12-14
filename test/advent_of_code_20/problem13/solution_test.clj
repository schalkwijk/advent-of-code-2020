(ns advent-of-code-20.problem13.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem13.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem13/input.txt") #"\n")]
      (is (= 295 (part1 input))))))

(deftest part2-test
  (testing "sample input"
    (let [input [0 "67,7,59,61"]]
      (is (= 754018 (part2 input))))
    (let [input [0 "1789,37,47,1889"]]
      (is (= 1202161486 (part2 input))))))
