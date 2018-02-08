

sensor "capteurNombreVoiture", {
    source "dataCar.json" with "LINEAR_INTERPOLATION", "a,b,c"
    noise(1, 1)
    offset "10/10/10"
}

markov_law "lumiDehors", {

    sunny >> 0.1 >> rainy
    sunny >> 0.1 >> rainy
    sunny >> 0.1 >> rainy

}
