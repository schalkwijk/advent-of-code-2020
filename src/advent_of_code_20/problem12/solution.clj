(ns advent-of-code-20.problem12.solution
  (:require [clojure.string :as str]))

(defn- to-int [string]
  (Integer/parseInt string))

(def vectors [[1 0] [0 -1] [-1 0] [0 1]])

(defn- parse-line [line]
  (let [characters (str/split line #"" 2)]
    {:instruction (first characters) :count (to-int (second characters))}))

(defn- rotate-ship [movement-vector angles direction]
  (let [v (if (= direction "R") 1 -1)
        number-rotations (* v (/ angles 90))]
    (mod (+ movement-vector number-rotations) (count vectors))))

(defn- add-coordinate [coordinate [dx dy] count]
  (reduce (fn [[x y] _] [(+ x dx) (+ y dy)]) coordinate (range 0 count)))

(defn- move-ship [{:keys [movement-vector] :as state} {:keys [instruction count]}]
  (case instruction
    "F" (update state :coordinates add-coordinate (nth vectors movement-vector) count)
    "N" (update state :coordinates add-coordinate (nth vectors 3) count)
    "S" (update state :coordinates add-coordinate (nth vectors 1) count)
    "E" (update state :coordinates add-coordinate (nth vectors 0) count)
    "W" (update state :coordinates add-coordinate (nth vectors 2) count)
    ("R" "L") (update state :movement-vector rotate-ship count instruction)))

(defn- compute-distance [{:keys [coordinates]}]
  (+ (Math/abs (first coordinates)) (Math/abs (last coordinates))))

(defn part1 [lines]
  (let [initial-state {:movement-vector 0 :coordinates [0 0]}]
    (compute-distance (reduce move-ship initial-state (map parse-line lines)))))

(defn rotate-ship-2 [coordinate angles direction]
  (->> (/ angles 90)
       (range 0)
       (reduce (fn [[x y] _] (if (= "R" direction) [y (* -1 x)] [(* -1 y) x])) coordinate)))

(defn- move-ship-2 [{:keys [movement-vector] :as state} {:keys [instruction count]}]
  (case instruction
    "F" (update state :coordinates add-coordinate movement-vector count)
    "N" (update state :movement-vector add-coordinate [0 1] count)
    "S" (update state :movement-vector add-coordinate [0 -1] count)
    "E" (update state :movement-vector add-coordinate [1 0] count)
    "W" (update state :movement-vector add-coordinate [-1 0] count)
    ("R" "L") (update state :movement-vector rotate-ship-2 count instruction)))

(defn part2 [lines]
  (let [initial-state {:movement-vector [10 1] :coordinates [0 0]}]
    (compute-distance (reduce move-ship-2 initial-state (map parse-line lines)))))
