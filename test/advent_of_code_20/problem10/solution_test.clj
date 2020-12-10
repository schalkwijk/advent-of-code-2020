(ns advent-of-code-20.problem10.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem10.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "first sample input"
    (let [input ["16" "10" "15" "5" "1" "11" "7" "19" "6" "12" "4"]]
      (is (= (* 7 5) (part1 input)))))
  (testing "second sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem10/input.txt") #"\n")]
      (is (= (* 22 10) (part1 input))))))

(deftest part2-test
  (testing "first sample input"
    (let [input ["16" "10" "15" "5" "1" "11" "7" "19" "6" "12" "4"]]
      (is (= 8 (part2 input)))))
  (testing "second sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem10/input.txt") #"\n")]
      (is (= 19208 (part2 input))))))
