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

(defn- compute-seat [current-seat occupied-neighbors threshold]
  (case current-seat
    :floor :floor
    :occupied (if (>= occupied-neighbors threshold) :empty :occupied)
    :empty (if (zero? occupied-neighbors) :occupied :empty)))

(defn- seat-single [grid [x y]]
  (let [occupied-neighbors (count-occupied-neighbors grid [x y])
        seat (compute-seat (nth (nth grid x) y) occupied-neighbors 4)]
    {:seat seat :location [x y]}))

(defn- update-seat [grid {:keys [seat location]}]
  (assoc-in grid [(first location) (second location)] seat))

(defn- seat-em [grid seating-method]
  (->> grid
       first
       count
       (range 0)
       (cartesian-product (range 0 (count grid)))
       (map #(seating-method grid %))
       (reduce update-seat grid)))

(def vectors [[-1 -1] [-1 0] [-1 1]
              [0 -1] [0 1]
              [1 -1] [1 0] [1 1]])

(defn- apply-vector [[dx dy] [x y] _]
  [(+ dx x) (+ dy y)])

(defn- occupied-vector [grid [x y] v]
  (->> 100000
       (range 0)
       (reductions (partial apply-vector v) [x y])
       rest
       (map (partial get-in grid))
       (filter (partial not= :floor))
       first
       (= :occupied)))

(defn- count-occupied-neighbors-2 [grid location]
  (count (filter (partial occupied-vector grid location) vectors)))

(defn- seat-single-2 [grid [x y]]
  (let [occupied-neighbors (count-occupied-neighbors-2 grid [x y])
        seat (compute-seat (nth (nth grid x) y) occupied-neighbors 5)]
    {:seat seat :location [x y]}))

(defn part1
  ([input] (part1 input 500))
  ([input iterations]
             (->> iterations
                  (range 0)
                  (reduce (fn [grid _] (seat-em grid seat-single)) (vec (map create-row input)))
                  count-occupied-seats)))

(defn part2
  ([input] (part2 input 500))
  ([input iterations]
   (->> iterations
        (range 0)
        (reduce (fn [grid _] (do (println (count-occupied-seats grid)) (seat-em grid seat-single-2))) (vec (map create-row input)))
        count-occupied-seats)))
