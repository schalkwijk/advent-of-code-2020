(ns advent-of-code-20.problem13.solution
  (:require [clojure.string :as str]))

(defn to-int [string]
  (Integer/parseInt string))

(defn- next-bus [departure-time schedule]
  {:next-bus (* schedule (inc (int (/ departure-time schedule)))) :bus-id schedule})

(defn- extract-solution [departure-time nearest-bus]
  (* (- (:next-bus nearest-bus) departure-time) (:bus-id nearest-bus)))

(defn part1 [input]
  (let [departure-time (to-int (first input))
        schedule (map to-int (filter (partial not= "x") (str/split (last input) #",")))]
    (->> schedule
         (map (partial next-bus departure-time))
         (apply min-key :next-bus)
         (extract-solution departure-time))))

(defn- create-schedule [times]
  (->> times
       (map-indexed (fn [offset time] {:bus-id time :offset offset}))
       (filter #(not= (:bus-id %) "x"))
       (map (fn [schedule] (update schedule :bus-id to-int)))))

(defn- valid? [next-bus current-time]
  (= 0 (mod (+ current-time (:offset next-bus)) (:bus-id next-bus))))

(defn- find-next-overlap [{:keys [buses current-time] :as state} next-bus]
  (let [lcm (apply * (map :bus-id buses))
        next-buses (conj buses next-bus)
        next-time (first (filter (partial valid? next-bus) (reductions + (+ lcm current-time) (repeat lcm))))]
    {:current-time next-time :buses next-buses}))

(defn part2 [input]
  (let [schedules (create-schedule (str/split (last input) #","))
        base-time (:bus-id (first schedules))
        result (reduce find-next-overlap {:buses [{:bus-id 1 :offset 0}] :current-time 0} schedules)]
    (:current-time result)))
