(ns advent-of-code-20.problem24.solution
  (:require [clojure.string :as str]))

(defn- parse-input-line [input-line]
  (map first (re-seq #"(e|se|sw|w|nw|ne)" input-line)))

(defn- flip-tile [tile]
  (if (or (nil? tile) (= :white tile)) :black :white))

(defn- add-vectors [[x y] [dx dy]]
  [(+ dx x) (+ dy y)])

(def vectors {"w" [-2 0] "e" [2 0] "se" [1 1] "sw" [-1 1] "nw" [-1 -1] "ne" [1 -1]})

(defn- navigate-to-and-flip-tile [state tile-path]
  (let [tile-location (reduce #(add-vectors %1 (get vectors %2)) [0 0] tile-path)]
    (update state tile-location flip-tile)))

(defn part1 [input]
  (count (filter #(= :black %) (vals (reduce navigate-to-and-flip-tile {} (map parse-input-line input))))))

(defn part2 [input] 10)
