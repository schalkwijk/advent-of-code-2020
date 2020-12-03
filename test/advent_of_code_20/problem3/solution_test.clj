(ns advent-of-code-20.problem3.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem3.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "first sample input"
    (let [input (map #(str/split % #"") ["..##......." "#...#...#.." ".#....#..#." "..#.#...#.#" ".#...##..#." "..#.##....." ".#.#.#....#" ".#........#" "#.##...#..." "#...##....#" ".#..#...#.#"])]
      (is (= 7 (part1 input))))))

(deftest part2-test
  (testing "first sample input"
    (let [input (map #(str/split % #"") ["..##......." "#...#...#.." ".#....#..#." "..#.#...#.#" ".#...##..#." "..#.##....." ".#.#.#....#" ".#........#" "#.##...#..." "#...##....#" ".#..#...#.#"])]
      (is (= 336 (part2 input))))))
