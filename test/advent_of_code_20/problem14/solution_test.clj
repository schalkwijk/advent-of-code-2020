(ns advent-of-code-20.problem14.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem14.solution :refer :all]))

(deftest part1-test
  (testing "applying mask"
    (let [mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
          number 11]
      (is (= 73 (apply-mask mask number)))))
  (testing "processing instructions"
    (let [input (str/split (slurp "test/advent_of_code_20/problem14/input.txt") #"\n")]
      (is (= 165 (part1 input))))))

(deftest part2-test
  (testing "processing instructions"
    (let [input (str/split (slurp "test/advent_of_code_20/problem14/input2.txt") #"\n")]
      (is (= 208 (part2 input))))))
