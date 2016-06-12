Continued fractions in Clojure.
-------------------------------

Inspired by Clojure's `Ratio` numeric type.

---

`(continued-fraction x)` returns the continued fraction representation
of `x` in the convention where the last element is greater than 1.

~~~ clojure
(def phi (/ (+ 1 (Math/sqrt 5)) 2)) ; ~ Golden ratio.
(take 10 (continued-fraction phi)) ;=> [1 1 1 1 1 1 1 1 1 1]
~~~

Given a continued fraction representation,
`(rational-approx cf-rep)` returns the corresponding rational number.  
`(rational-approx n x)` gives the nth rational 
approximation (convergent) of x.

~~~ clojure
(rational-approx 1 Math/PI) ;=> 22/7
(rational-approx 3 Math/PI) ;=> 355/133
~~~

`(successive-approx x)` gives a list of the successive rational 
approximations (convergents) to x,   
so that `rationalize x` == `(last (successive-approx x))` == 
`(rational-approx (continued-fraction x))`.

Note that when computing the `continued-fraction` representation of a 
decimal approximation to an irrational number, the returned cfrac-rep
will differ from the "actual" cfrac-rep at some point.  For example

~~~ clojure
(vec (continued-fraction phi)) ;=> [1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 
                               ;    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 
                               ;    1 1 1 1 4 37 6 2 3 8 19 1 3]
~~~

This happens at the term in the continued fraction where the rational 
approximation becomes more precise than the decimal representation.

~~~ clojure
(identity phi) ;=> 1.618033988749895
(double (rational-approx (take 37 (repeat 1)))) ;=> 1.618033988749894
(double (rational-approx (take 38 (repeat 1)))) ;=> 1.618033988749895
(double (rational-approx (take 39 (repeat 1)))) ;=> 1.618033988749895
~~~

The cfrac-reps can be used directly to calculate decimal represenations
to very high precision.

~~~ clojure
(frac->dec (rational-approx (take 50 (repeat 1)))) ;=> [1 6 1 8 0 3 3 9 8 8
                                                   ;    7 4 9 8 9 4 8 4 8 2 
                                                   ;    0 7 4 0 9...] 
(def sqrt2 (cons 1 (repeat 2)))
(frac->dec (rational-approx (take 50 sqrt2))) ;=> [1 4 1 4 2 1 3 5 6 2 3 7 3 
                                              ;    0 9 5 0 4 8 8 0 1 6 8 8...] 
~~~
---
(c) Andrew Lytle (2014)
