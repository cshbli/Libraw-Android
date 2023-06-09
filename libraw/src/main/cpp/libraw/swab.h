#ifndef __SWAB_H__
#define __SWAB_H__
#include <stdint.h>
#include <sys/types.h>
#ifdef __cplusplus
extern "C" {
#endif
void swab(const void *from, void *to, ssize_t n);
#ifdef __cplusplus
}
#endif
#endif
