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

(defn- tile-configuration [input]
  (reduce navigate-to-and-flip-tile {} (map parse-input-line input)))

(defn part1 [input]
  (count (filter #(= :black %) (vals (tile-configuration input)))))

(defn- find-surrounding-white-tiles [state location]
  (filter #(not= :black (get state %)) (map #(add-vectors location %) (vals vectors))))

(defn- find-surrounding-black-tiles [state location]
  (filter #(= :black (get state %)) (map #(add-vectors location %) (vals vectors))))

(defn- maybe-flip-black-tile [state tile]
  (let [surrounding-black-tiles (count (find-surrounding-black-tiles state tile))]
    [tile (if (or (= 0 surrounding-black-tiles) (> surrounding-black-tiles 2)) :white :black)]))

(defn- maybe-flip-white-tile [state tile]
  (let [surrounding-black-tiles (count (find-surrounding-black-tiles state tile))]
    [tile (if (= surrounding-black-tiles 2) :black :white)]))

(defn- maybe-flip-tile [state tile]
  ((if (= :black (get state tile)) maybe-flip-black-tile maybe-flip-white-tile) state tile))

(defn- game-of-life-it [state]
  (let [black-tiles (map first (filter #(= :black (second %)) state))
        white-tiles (set (mapcat #(find-surrounding-white-tiles state %) black-tiles))]
    (->> black-tiles
         (concat white-tiles)
         (map #(maybe-flip-tile state %))
         (reduce #(assoc %1 (first %2) (second %2)) state))))

(defn part2
  ([input] (part2 input 100))
  ([input rounds]
   (let [initial-tile-configuration (tile-configuration input)]
     (->> rounds
          (range 0)
          (reduce (fn [state _] (game-of-life-it state)) initial-tile-configuration)
          vals
          (filter #(= :black %))
          count))))
