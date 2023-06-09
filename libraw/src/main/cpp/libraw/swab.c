#include "swab.h"
#include <byteswap.h>
void swab(const void *from, void *to, ssize_t n) {
    const int16_t *in = (int16_t*)from;
    int16_t *out = (int16_t*)to;
    n /= 2;
    for (ssize_t i = 0 ; i < n; ++i) {
        out[i] = bswap_16(in[i]);
    }
}