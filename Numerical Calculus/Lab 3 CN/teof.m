a = [10, 7, 8, 7;7,5,6,5; 8,6,10,9;7,5,9,10];
b = [32;23;33;31];
xe = a\b;
det(a);
inv(a);

%peturbam membrul drept
bb = [32.1,22.9,33.1,30.9];
xp1 = a\b;
eri1=norm(bb-b)/norm(bb);


ap = [10,7,8.1,7.2;7.08,5.04,6,5;8,5.98,9.89,9;6.99,4.99,9,9.98];

%perturbam matricea
xp2 = ap\b;
eri2 = norm(a-ap)/norm(a);
ero2 = norm(xe-xp2)/norm(xe);
eri2\ero2
cond(a)