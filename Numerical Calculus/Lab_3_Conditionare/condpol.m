function nc = condpol(p,r)
% conditionarea unei ecuatii polinomiale p(x)=0
% p polinom de forma '[...]'
% r radacinile polinomului de forma '[...]'

    if nargin<2
        r = roots(p);
    end
    
    % calculam derivata p'(x)
    % derivata are cu 1 mai putini termeni in polinomul asociat
    n = length(p)-1;                  
    dp = [n:-1:1] .* p(1:end-1); %derivata;
    val_df = polyval(dp,r); % calculam val derivatei in fiecare radacina
    poliv = polyval(abs(p(2:end)),abs(r));
    nc = poliv./(abs(r.*val_df));
end