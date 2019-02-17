(defun insertie(l a poz)
    (cond
        ((null l)nil)
        ((equal 1 (MOD poz 2))(append (car l insertie(cdr l) a (+ poz 1)))))
        (T(append (car l) a insertie(cdr l a (+ poz 1))))
    )
