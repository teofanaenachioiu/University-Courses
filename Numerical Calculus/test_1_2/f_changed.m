function f = f_changed(x, alfa)
    syms h;
    if x < alfa && x > -1*alfa
        % T = taylor(h^2/(cos(sin(h))^2-1), h, 'Order', 5);
        % subs(T,h,x) 
        % f = subs(T,h,x); 
        T = @(h) - (2*h^2)/3 - (2*h^4)/15 - 1;
        f = T(x);
    else
        f = x^2/(cos(sin(x))^2-1);
    end
   