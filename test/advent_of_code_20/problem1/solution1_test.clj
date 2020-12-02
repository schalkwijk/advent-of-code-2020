(ns advent-of-code-20.problem1.solution1-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem1.solution1 :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input [1721 979 366 299 675 1456]]
      (is (= 514579 (part1 input))))))

(deftest part2-test
  (testing "sample input"
    (let [input [1721 979 366 299 675 1456]]
      (is (= 241861950 (part2 input))))))
