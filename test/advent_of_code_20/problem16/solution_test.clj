(ns advent-of-code-20.problem16.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem16.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem16/input.txt") #"\n\n")]
      (is (= 71 (part1 input))))))

(deftest part2-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem16/input2.txt") #"\n\n")]
      (is (= [["row" 0] ["class" 1] ["seat" 2]] (categorize-seats (process-input input)))))))
