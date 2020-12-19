(ns advent-of-code-20.problem18.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem18.solution :refer :all]))


(deftest part1-test
  (comment (testing "sample input"
     (let [input ["1 + 2"]]
       (is (= 3 (first (part1 input)))))
     (let [input ["1 * 2 + 3"]]
       (is (= 5 (first (part1 input)))))
     (let [input ["1 + (2 * 3)"]]
       (is (= 7 (first (part1 input)))))))
  (testing "create-expression"
    (let [input "1 + 2"]
      (is (= [1 "+" 2] (create-expression input))))
    (let [input "1 + (2 * 3)"]
      (is (= [1 "+" [2 "*" 3]] (create-expression input))))
    (let [input "(6 + (3 * 6) * 5) * 8 + 2 * 2"]
      (is (= [[6 "+" [3 "*" 6] "*" 5] "*" 8 "+" 2 "*" 2] (create-expression input))))
    (let [input "4 + 6 * 7 * ((2 + 2 * 8) * 3 + 2) * 5"]
      (is (= [4 "+" 6 "*" 7 "*"[[2 "+" 2 "*" 8] "*" 3 "+" 2] "*" 5] (create-expression input)))))
  (testing "evaluate-expression"
    (let [input "1 + 2"]
      (is (= 3 (evaluate-expression input))))))
