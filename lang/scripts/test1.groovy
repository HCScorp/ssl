

sensor "capteurNombreVoiture", {
    source "dataCar.json" with "LINEAR_INTERPOLATION", "a,b,c"
    noise(1, 1)
    offset "10/10/10"
}

markov_law "lumiDehors", {
    state "sunny" with 0.1 give "rainy"
    state "sunny" with 0.9 give "sunny"
    state "rainy" with 0.5 give "sunny"
    state "rainy" with 0.5 give "rainy"
}