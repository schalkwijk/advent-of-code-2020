(ns advent-of-code-20.problem2.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem2.solution :refer :all]))

(deftest part1-test
  (testing "first sample input"
    (let [input ["1-3 a: abcde"]]
      (is (= 1 (part1 input)))))
  (testing "second sample input"
    (let [input ["1-3 b: cdefg"]]
      (is (= 0 (part1 input)))))
  (testing "third sample input"
    (let [input ["2-9 c: ccccccccc"]]
      (is (= 1 (part1 input)))))
  (testing "full sample input"
    (let [input ["1-3 a: abcde" "1-3 b: cdefg" "2-9 c: ccccccccc"]]
      (is (= 2 (part1 input))))))

(deftest part2-test
  (testing "first sample input"
    (let [input ["1-3 a: abcde"]]
      (is (= 1 (part2 input)))))
  (testing "second sample input"
    (let [input ["1-3 b: cdefg"]]
      (is (= 0 (part2 input)))))
  (testing "third sample input"
    (let [input ["2-9 c: ccccccccc"]]
      (is (= 0 (part2 input)))))
  (testing "full sample input"
    (let [input ["1-3 a: abcde" "1-3 b: cdefg" "2-9 c: ccccccccc"]]
      (is (= 1 (part2 input))))))
