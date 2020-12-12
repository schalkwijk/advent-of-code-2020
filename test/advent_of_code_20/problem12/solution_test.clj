(ns advent-of-code-20.problem12.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem12.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input ["F10"]]
      (is (= 10 (part1 input))))
    (let [input ["F10" "N3"]]
      (is (= 13 (part1 input))))
    (let [input ["F10" "N3" "F7"]]
      (is (= 20 (part1 input))))
    (let [input ["F10" "N3" "F7" "R90"]]
      (is (= 20 (part1 input))))
    (let [input ["F10" "N3" "F7" "R90" "F11"]]
      (is (= 25 (part1 input))))))
