(ns advent-of-code-20.problem20.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem20.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem20/input.txt") #"\n\n")]
      (is (= 20899048083288 (dec (part1 input)))))))
