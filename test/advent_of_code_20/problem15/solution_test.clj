(ns advent-of-code-20.problem15.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem15.solution :refer :all]))

(deftest part1-test
  (testing "sample input"
    (let [input ["1,3,2"]]
      (is (= 1 (part1 input))))
    (let [input ["0,3,6"]]
      (is (= 436 (part1 input))))
    (let [input ["2,3,1"]]
      (is (= 78 (part1 input))))))
