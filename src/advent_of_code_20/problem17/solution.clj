(ns advent-of-code-20.problem17.solution
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as comb]))

(defn- parse-input [input dimensions]
  (let [grid (map #(str/split % #"") input)]
    (->> grid
         (map-indexed (fn [index row] (map-indexed #(apply vector (= "#" %2) index %1 (repeat (- dimensions 2) 0)) row)))
         (apply concat)
         (filter #(first %))
         (reduce #(assoc-in %1 (rest %2) (first %2)) {}))))

(defn- get-w [state]
  (mapcat keys (mapcat vals (mapcat vals (vals state)))))

(defn- find-maxes [state dimensions]
  (let [xs (keys state)
        ys (mapcat keys (vals state))
        zs (mapcat keys (mapcat vals (vals state)))
        ranges (if (= dimensions 3) [xs ys zs] [xs ys zs (get-w state)])]
    (map (fn [r] [(apply min r) (apply max r)]) ranges)))

(defn- find-squares [state ranges]
  (let [final-ranges (map #(range (dec (first %)) (+ 2 (last %))) ranges)]
    (apply comb/cartesian-product final-ranges)))

(def vectors
  (memoize (fn [dimensions] (filter (fn [v] (not (every? #(= 0 %) v))) (apply comb/cartesian-product (map (fn [_] (range -1 2)) (range 0 dimensions)))))))

(defn add-vector [square v]
  (map (partial apply +) (map vector square v)))

(defn- count-active-neighbors [state square dimensions]
  (->> (vectors dimensions)
       (map #(add-vector square %))
       (filter #(get-in state % false))
       count))

(defn- toggle [old-state new-state square dimensions]
  (let [active-neighbors (count-active-neighbors old-state square dimensions)
        currently-active (get-in old-state square false)
        next-active (if currently-active (or (= 2 active-neighbors) (= 3 active-neighbors)) (= 3 active-neighbors))]
    (if next-active (assoc-in new-state square next-active) new-state)))

(defn- step [state dimensions]
  (let [maxes (find-maxes state dimensions)
        squares (find-squares state maxes)]
    (reduce #(toggle state %1 %2 dimensions) {} squares)))

(defn part1
  ([input] (part1 input 3))
  ([input dimensions]
   (let [result (->> 6
                     (range 0)
                     (reduce (fn [state _] (step state dimensions)) (parse-input input dimensions))
                     vals
                     (mapcat vals)
                     (mapcat vals))]
     (if (= dimensions 3) (count result) (count (mapcat vals result))))))

(defn part2 [input] (part1 input 4))
