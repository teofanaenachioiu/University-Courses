function [f, sin_sign, cos_sign] = reduce_cadran(x)
    x = mod(x, 2*pi);
    if (x >= 0 && x < pi/2)
        % primul cadran +, +
        sin_sign = 1;
        cos_sign = 1;
        f = x; 
    elseif (x >= pi/2 && x < pi)
        % al doilea cadran +, -
        sin_sign = 1;
        cos_sign = -1;
        f = pi - x;
    elseif (x >= pi && x < 3*pi/2)
         % al treilea cadran -, -
        sin_sign = -1;
        cos_sign = -1;
        f = x - pi;
    else
        % al patrulea cadran -, +
        sin_sign = -1;
        cos_sign = 1;
        f = 2*pi - x;
    end