(ns advent-of-code-20.problem11.solution
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :refer :all]))

(defn- create-row [raw-row]
  (vec (map #(if (= "L" %) :empty :floor)(str/split raw-row #""))))

(defn- count-occupied-seats [grid]
  (count (filter #(= :occupied %) (flatten grid))))

(def neighbors
  [[-1 -1] [-1 0] [-1 1]
   [0 -1] [0 1]
   [1 -1] [1 0] [1 1]])

(defn- count-occupied-neighbors [grid [x y]]
  (->> neighbors
       (map (fn [[dx dy]] [(+ x dx) (+ y dy)]))
       (map (partial get-in grid))
       (filter (partial = :occupied))
       count))

(defn- compute-seat [current-seat occupied-neighbors]
  (case current-seat
    :floor :floor
    :occupied (if (>= occupied-neighbors 4) :empty :occupied)
    :empty (if (zero? occupied-neighbors) :occupied :empty)))

(defn- seat-single [grid [x y]]
  (let [occupied-neighbors (count-occupied-neighbors grid [x y])
        seat (compute-seat (nth (nth grid x) y) occupied-neighbors)]
    {:seat seat :location [x y]}))

(defn- update-seat [grid {:keys [seat location]}]
  (assoc-in grid [(first location) (second location)] seat))

(defn- seat-em [grid]
  (->> grid
       first
       count
       (range 0)
       (cartesian-product (range 0 (count grid)))
       (map #(seat-single grid %))
       (reduce update-seat grid)))

(defn part1
  ([input] (part1 input 500))
  ([input iterations]
             (->> iterations
                  (range 0)
                  (reduce (fn [grid _] (seat-em grid)) (vec (map create-row input)))
                  count-occupied-seats)))

(defn part2 [input] 10)
