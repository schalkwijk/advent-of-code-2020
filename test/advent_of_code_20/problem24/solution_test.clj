(ns advent-of-code-20.problem24.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem24.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem24/input.txt") #"\n")]
      (is (= 9 (dec (part1 input)))))))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem24/input.txt") #"\n")]
      (is (= 2207 (dec (part2 input)))))))
