(ns advent-of-code-20.problem25.solution
  (:require [clojure.string :as str]))

(defn- to-int [string] (Integer/parseInt string))

(defn- determine-loop-size [key subject]
  (reduce #(if (= key %1) (reduced %2) (mod (* %1 subject) 20201227)) 1 (range)))

(defn part1 [input]
  (let [card-public-key (to-int (first input))
        door-public-key (to-int (second input))
        door-loop-size (determine-loop-size door-public-key 7)
        card-loop-size (determine-loop-size card-public-key 7)]
    (reduce (fn [s _] (mod (* s card-public-key) 20201227)) 1 (range 0 door-loop-size))))

(defn part2 [input] 10)
