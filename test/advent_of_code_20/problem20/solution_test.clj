(ns advent-of-code-20.problem20.solution-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent-of-code-20.problem20.solution :refer :all]))

(defn- extract-tile [tiles id]
  (get (into {} (map (fn [tile] [(:id tile) tile]) tiles)) id))

{1 {"2311-8" true}, 2 {"2729-8" true, "2729-9" true}}

(deftest part1-test
  (testing "matching-tiles"
    (let [tiles (mapcat create-tiles (str/split (slurp "test/advent_of_code_20/problem20/input.txt") #"\n\n"))
          matching (matching-tiles tiles)]
      (is (= ["2311-5"] (map :id (get-in matching ["1951-2" right]))))))
  (testing "sample input"
    (let [input (str/split (slurp "test/advent_of_code_20/problem20/input.txt") #"\n\n")]
      (comment (is (= 20899048083289 (part1 input)))))))
