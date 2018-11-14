interclasare(L1, [], L1):-!.
interclasare([], L2, L2):-!.

interclasare([H1|T1], [H2|T2], [H1|TR]):-
    H1 < H2,!,
    interclasare(T1, [H2|T2], TR).

interclasare([H1|T1], [H2|T2], [H2|TR]):-
    H1 >= H2,!,
    interclasare([H1|T1], T2, TR).
