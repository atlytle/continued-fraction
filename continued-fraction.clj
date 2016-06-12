;;; continued-fraction.clj
;;; Implements continued fractions and rational approximations.

;;; Andrew Lytle (2014)

(ns cfrac)

(defn continued-fraction
  "Continued fraction representation of x."
  ([a b]
    (if (not (zero? b))
        (cons (int (quot a b)) (lazy-seq (continued-fraction b (mod a b))))))
  ([x]
    (continued-fraction (rationalize x) 1)))


(defn rational-approx
  "nth order rational approximation to x."
  ([cf-rep]
    (reduce #(+ %2 (/ 1 (+ %1))) (reverse cf-rep)))
  ([n x]
    (rational-approx (take (inc n) (continued-fraction x)))))


(defn take-until
  "Variation of (take-while (complement pred) coll),
  retains the first element to fail (complement pred)."
  [pred coll]
  (let [[before after] (split-with (complement pred) coll)]
    (conj (vec before) (first after))))


(defn successive-approx
  "Successive rational approximations to x."
  [x]
  (take-until (partial == (rationalize x))
              (map #(rational-approx % x) (range))))

(defn frac->dec
  "Convert a rational number to decimal. Returns a seq of the digits."
  ([frac]
    (map int (frac->dec (numerator frac) (denominator frac))))
  ([n d]
    (if (zero? (mod n d))
        (cons (quot n d) [])
        (cons (quot n d) (lazy-seq (frac->dec (* 10 (mod n d)) d))))))
