(ns advent-of-code-20.problem6.solution
  (:require [clojure.math.combinatorics :refer :all]
            [clojure.string :as str]))

(defn- answers-per-group [group]
  (let [answers (mapcat #(str/split % #"") group)]
    (count (set answers))))

(defn part1 [answers]
  (let [groups (map #(str/split % #"\n") answers)]
    (apply + (map answers-per-group groups))))

(defn- all-answers-per-group [group]
  (let [answers (mapcat #(str/split % #"") group)]
    (count (filter (fn [[answer c]] (= c (count group)))(frequencies answers)))))

(defn part2 [answers]
  (let [groups (map #(str/split % #"\n") answers)]
    (apply + (map all-answers-per-group groups))))
