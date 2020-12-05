(ns advent-of-code-20.problem5.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem5.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "splitting"
    (is (= [0 63] (split :bottom [0 127])))
    (is (= [32 63] (split :top [0 63])))
    (is (= [32 47] (split :bottom [32 63])))
    (is (= [40 47] (split :top [32 47])))
    (is (= [44 47] (split :top [40 47])))
    (is (= [44 45] (split :bottom [44 47])))
    (is (= [44 44] (split :bottom [44 45]))))
  (testing "finding row"
    (is (= 44 (find-row "FBFBBFF")))
    (is (= 70 (find-row "BFFFBBF")))
    (is (= 14 (find-row "FFFBBBF")))
    (is (= 102 (find-row "BBFFBBF"))))
  (testing "finding column"
    (is (= 5 (find-column "RLR")))
    (is (= 7 (find-column "RRR")))'
    (is (= 4 (find-column "RLL"))))
  (testing "finding seat id"
    (is (= 567 (find-seat "BFFFBBFRRR")))
    (is (= 119 (find-seat "FFFBBBFRRR")))
    (is (= 820 (find-seat "BBFFBBFRLL")))))
