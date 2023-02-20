MODULE_NAME=Amantya-NIAB-5gsacore

# SIL Makefile for YumaPro Server Instrumentation Library
#

############### SOURCE PROFILE ##############################

SUBDIR_NM=$(MODULE_NAME)

SUBDIR_CPP=

############### TARGET PROFILE ##############################

ifdef MAC
 INSTALL_PREFIX ?= /usr/local
else
 INSTALL_PREFIX ?= /usr
endif

TARGET=../bin

LIB_INST=../lib

ifdef YUMAPRO_HOME
WORK_INST=$(YUMAPRO_HOME)/target/lib
else
WORK_INST=
endif

ifdef LIB64
REAL_INST=$(DESTDIR)$(INSTALL_PREFIX)/lib64/yumapro
REAL_BASE=$(DESTDIR)$(INSTALL_PREFIX)/lib64
else
REAL_INST=$(DESTDIR)$(INSTALL_PREFIX)/lib/yumapro
REAL_BASE=$(DESTDIR)$(INSTALL_PREFIX)/lib
endif


#################### MAKE RULES ########################

all: sil_dummy sil_lib

#################### PLATFORM DEFINITIONS ############
CINCBASE=$(DESTDIR)$(INSTALL_PREFIX)/include

CINC = $(USER_INC) -I.

ifdef FORCE_YUMAPRO_HOME
CINC += -I$(YUMAPRO_HOME)/src/platform -I$(YUMAPRO_HOME)/src/ncx \
     -I$(YUMAPRO_HOME)/src/agt
else
 ifdef NO_GNU
CINC += -I$(CINCBASE)/yumapro/platform -I$(CINCBASE)/yumapro/ncx \
     -I$(CINCBASE)/yumapro/agt
 else
CINC += -iquote $(CINCBASE)/yumapro/platform -iquote $(CINCBASE)/yumapro/ncx \
     -iquote $(CINCBASE)/yumapro/agt
 endif   # NO_GNU
endif   # FORCE_YUMAPRO_HOME


CINC += -I$(CINCBASE) -I$(CINCBASE)/libxml2 -I$(CINCBASE)/libxml2/libxml

ifdef FREEBSD
CINC += -I${SYSROOT}/usr/local/include -I${SYSROOT}/usr/local/include/libxml2 \
     -I${SYSROOT}/usr/local/include/libxml2/libxml
endif


ifdef COVERITY
 ifneq "$(INSTALL_PREFIX)" "/usr"
   CINC += -I${SYSROOT}/usr/include -I${SYSROOT}/usr/include/libxml2 \
   -I${SYSROOT}/usr/include/libxml2/libxml
 endif
endif  # COVERITY

ifdef MAC
# MACOSX version
XCODE ?= /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.11.sdk/usr/include

  CFLAGS += -DMACOSX=1
  CINC += -I$(XCODE) \
    -I$(XCODE)/libxml2 \
    -I$(XCODE)/libxml2/libxml \
    -I$(XCODE)/openssl
endif

ifdef DESTDIR
OWNER=
else
ifdef MAC
OWNER=-oroot
else
ifdef CYGWIN
OWNER=
else
OWNER= --owner=root
endif
endif
endif

### GCC + [LINUX or MACOSX]

CDEFS=

ifdef USE_WERROR
WERROR= -Werror
else
WERROR= -Werror=incompatible-pointer-types
endif

ifdef NO_YPACK
PACK_WARN=
CDEFS += -DNO_YPACK=1
else
PACK_WARN=-Wno-address-of-packed-member
endif

CWARN=-Wall -Wno-long-long -Wformat-y2k -Winit-self \
	-Wswitch-default -Wextra -Wundef -Wshadow -Wpointer-arith \
	-Wwrite-strings -Wbad-function-cast -Wcast-qual \
	-Waggregate-return -Wstrict-prototypes -Wold-style-definition \
	-Wmissing-prototypes -Wmissing-declarations \
	-Winvalid-pch -Wredundant-decls -Wnested-externs -Winline \
        -std=gnu99 $(WERROR) $(PACK_WARN)

YDPARMS= --indent=4 --module=$(SUBDIR_NM)

# -Wunreachable-code removed due to -O3
# -O3 changed to -O2 due to code bloat from inline functions

CDEFS += -DDEBUG=1 -DLINUX=1 -DGCC=1

ifndef NOFLOAT
  CDEFS += -DHAS_FLOAT=1
endif


ifdef DEBUG2
CDEFS += -DDEBUG2=1
endif

ifdef DEBUG3
CDEFS += -DDEBUG3=1
endif

ifdef DEBUG4
CDEFS += -DDEBUG4=1
endif

ifdef FREEBSD
CDEFS += -DFREEBSD=1
endif

ifdef KEY
  CDEFS += -DKEY=$(KEY)
endif

ifndef NOFLOAT
  CDEFS += -DHAS_FLOAT=1
endif

ifdef LIB64
  CDEFS += -DLIB64=1
endif

ifdef PATCH_RELEASE
  CDEFS += -DPATCH_RELEASE=1
endif

ifdef UCLINUX
   CDEFS += -DUCLINUX=1
endif

ifdef BITBAKE
   CDEFS += -DBITBAKE=1
endif

ifdef STATIC_SERVER
 CDEFS += -DSTATIC_SERVER=1
endif # STATIC_SERVER

ifdef WITH_YCONTROL
  CDEFS += -DWITH_YCONTROL=1
endif

# production (0) or debug (1) build
ifdef DEBUG
  CFLAGS += -ggdb3
else
  CFLAGS += -O2
endif

ifdef PTHREADS
  CFLAGS += -DPTHREADS=1
  CFLAGS += -pthread
endif

CFLAGS += $(CDEFS) $(CWARN)

ifndef CYGWIN
 ifndef UCLINUX
  ifndef STATIC
   CFLAGS += -fPIC
  endif # STATIC
 endif # UCLINUX
endif # CYGWIN

# memory leak debugging mode
ifdef MEMTRACE
  CFLAGS += -DMEMORY_DEBUG=1
endif

ifdef RELEASE
  CFLAGS += -DRELEASE=$(RELEASE)
endif

ifdef LIB64
   CFLAGS += -DLIB64=1
endif


ifdef MAC
   GRP=
else
 ifdef DESTDIR
   GRP=
 else
  ifdef CYGWIN
   GRP=
  else
   GRP=--group=root
  endif # CYGWIN
 endif # DESTDIR
endif # MAC

ifdef STATIC
LIBSUFFIX=a
else
 ifdef MAC
  ifdef COVERITY
LIBSUFFIX=so
  else
LIBSUFFIX=dylib
  endif # COVERITY
else
LIBSUFFIX=so
endif # MAC
endif # STATIC

ifdef CPP98
  CXXFLAGS += -std=c++98 -Wno-c++0x-compat -DCPP98
  $(info Compiling in C++98 mode)
else ifdef UCLINUX
  CFLAGS += -D__USE_UNIX98=1
  CXXFLAGS += -std=c++98 -Wno-c++0x-compat -DCPP98
  $(info Compiling in UCLINUX C++98 mode)
else
  CXXFLAGS += -std=c++0x
  $(info Compiling in C++11 mode)
endif  # CPP98

ifdef EXTRA_CFLAGS
CFLAGS += $(EXTRA_CFLAGS)
endif


ifdef CLANG
  CC = clang
  CCPP = clang++
endif

ifdef CLANG_ASAN
  CC= clang -O1 -fsanitize=address -fno-omit-frame-pointer
  CCPP= clang++ -O1 -fsanitize=address -fno-omit-frame-pointer
  LINK= clang++ -g -fsanitize=address
endif


ifdef BITBAKE
  CCPP = $(CXX)
endif

ifdef COVERITY
  CC = gcc
endif

ifdef WINDOWS
  CC ?= i586-mingw32msvc-gcc
  CCPP ?= i586-mingw32msvc-g++
  LINK ?= i586-mingw32msvc-g++
  RANLIB ?= i586-mingw32msvc-ranlib
else
 ifdef CROSS_TARGET
    CC = $(CROSS_TARGET)gcc
    CCPP = $(CROSS_TARGET)g++
    AR = $(CROSS_TARGET)ar
    RANLIB = $(CROSS_TARGET)ranlib
    LD = $(CROSS_TARGET)ld
 else
  ifdef UCLINUX
    CC = $(CROSS_COMPILE_APPS)gcc
    CCPP = $(CROSS_COMPILE_APPS)g++
    AR = $(CROSS_COMPILE_APPS)ar
    RANLIB = $(CROSS_COMPILE_APPS)ranlib
    LD = $(CROSS_COMPILE_APPS)ld
  else
   ifdef PACKAGE_BUILD
    CC ?= gcc
    CCPP ?= g++
   else
    ifdef GPROFILE
     CC ?= gcc
     CCPP ?= g++
    else
     ifdef CLANG
     CC ?= clang
     else
     CC ?= gcc
     CCPP ?= g++
     endif # CLANG
    endif # GPROFILE
   endif # PACKAGE_BUILD
  endif # UCLINUX
 endif # CROSS_TARGET
endif # WINDOWS

# use this version if C++ is supported
# LINK ?= $(CCPP)

# use this version if C is supported
LINK ?= $(CC)

AR ?= $(CROSS_TARGET)ar
RANLIB ?= $(CROSS_TARGET)ranlib

LINT=splint
LINTFLAGS= '-weak -macrovarprefix "m_"'
##LIBFLAGS=-lsocket

#LFLAGS=-v --no-as-needed
LFLAGS=-lm
LPATH=$(USER_LIB) -L$(LIB_INST)

CEES = $(wildcard *.c)

HEES = $(wildcard *.h)

################ OBJS RULE #############
OBJS = $(patsubst %.c,$(TARGET)/%.o,$(CEES))

################ DEPS RULE #############
DEPS = $(patsubst %.c,%.D,$(wildcard *.c))

######################## PLATFORM DEFINITIONS #############
PLATFORM_CPP=

######################### MAKE DEPENDENCIES ###############
COMPILE.c= $(CC) $(CFLAGS) $(CPPFLAGS) $(PLATFORM_CPP) \
           $(CINC) $(SUBDIR_CPP) $(TARGET_ARCH) -c


$(TARGET)/%.o: %.c
	$(CC) $(CFLAGS) $(CPPFLAGS) $(PLATFORM_CPP) \
        $(CINC) $(SUBDIR_CPP) $(TARGET_ARCH) -c -o $@ $<


# Common library rule

$(LIB_INST)/lib%.a: $(OBJS)
	$(AR) cr $@ $(OBJS)
	$(RANLIB) $@

# dependency rule to make temp .D files from .c sources
# all the .D files are collected and appended to the
# appropriate Makefile when 'make depend' is run
# this rule is kept here to make sure it matches COMPILE.c
%.D: %.c
	$(CC) -MM -MG -MT $(TARGET)/$(patsubst %.c,%.o,$<) \
	-Wall -Wcomment $(CPPFLAGS) $(PLATFORM_CPP) $(CINC) \
	$(SUBDIR_CPP) $(TARGET_ARCH) -c $< > $@


################## MAKE DEPENDENCIES #####################
# following depend rule is the GNU version! Other versions TBD
depend: dependencies

dependencies: $(DEPS)
	@if [ ! -f Makefile ]; then \
	    echo "Error: Makefile missing!"; \
	    exit 1; \
	fi
	@rm -f dependencies
	@for i in $(DEPS); do \
	  if [ -f $$i ] ; then \
	    (cat $$i >> dependencies; echo "" >> dependencies) ; \
	  else \
	    (echo "*** Warning: Dependency file $i.D is missing! (Skipping...) ***"; \
	     echo "# Warning: Missing file $$i !!!") ; \
	  fi; \
	done
	@echo "" >> dependencies
# delete the .D files to force make depend to rebuild them each time
# that target is built
#	@rm -f $(DEPS)


test:

install:
	mkdir -p $(REAL_INST)
	install $(LIB_INST)/lib$(SUBDIR_NM).$(LIBSUFFIX) $(REAL_INST)

uninstall:
	rm -f $(REAL_INST)/lib$(SUBDIR_NM).$(LIBSUFFIX)

work:
ifdef YUMAPRO_HOME
	mkdir -p $(WORK_INST)
	install $(LIB_INST)/lib$(SUBDIR_NM).$(LIBSUFFIX) $(WORK_INST)
endif

sil_lib: $(LIB_INST)/lib$(SUBDIR_NM).$(LIBSUFFIX)

# this dummy rule keeps make from deleting the $(OBJS) as
# intermediate files
sil_dummy: dependencies $(OBJS)

clean:
	rm -f $(OBJS) $(LIB_INST)/lib$(SUBDIR_NM).*

superclean:
	rm -f *~  $(DEPS) dependencies $(OBJS) $(LIB_INST)/lib$(SUBDIR_NM).*
	rm -rf ../output/*

ifndef MAC
$(LIB_INST)/lib$(SUBDIR_NM).so: $(OBJS)
	$(LINK) $(CFLAGS) $(CPPFLAGS) $(LDFLAGS) -shared -rdynamic -Wl,-soname,lib$(SUBDIR_NM).so -L/usr/lib/jvm/java-11-openjdk-amd64/lib/server -o $@ $(OBJS) -ljvm
else
 ifdef COVERITY
$(LIB_INST)/lib$(SUBDIR_NM).so: $(OBJS)
	$(LINK) $(CFLAGS) $(CPPFLAGS) $(LDFLAGS) -shared -rdynamic -Wl,-soname,lib$(SUBDIR_NM).so -o $@ $(OBJS)
 else
$(LIB_INST)/lib$(SUBDIR_NM).so: $(OBJS)
	$(LINK) $(CFLAGS) $(CPPFLAGS) $(LDFLAGS) -shared -bundle -std=gnu99 \
	-current_version 1.0 -undefined dynamic_lookup \
	-o $@ -install_name lib$(SUBDIR_NM).so $(OBJS)
 endif  # COVERITY
endif  # MAC

code:
	yangdump-sdk format=h $(YDPARMS) output=$(SUBDIR_NM).h
	yangdump-sdk format=c $(YDPARMS) output=$(SUBDIR_NM).c

splitcode:
	yangdump-sdk format=yh $(YDPARMS) output=y_$(SUBDIR_NM).h
	yangdump-sdk format=yc $(YDPARMS) output=y_$(SUBDIR_NM).c
	yangdump-sdk format=uh $(YDPARMS) output=u_$(SUBDIR_NM).h
	yangdump-sdk format=uc $(YDPARMS) output=u_$(SUBDIR_NM).c


.PHONY: all superclean clean test install uninstall \
        distclean depend lint code splitcode work sil_lib sil_dummy


# prevent the make program from choking on all the symbols
# that get generated from autogenerated make rules
.NOEXPORT:

include dependencies
