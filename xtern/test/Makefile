#
# Copyright (c) 2013,  Regents of the Columbia University 
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
#
# 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
#
# 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other 
# materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
# THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
# CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
# LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
# IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#

#===- test/Makefile ----------------------------------------*- Makefile -*--===#
# 
#                     The LLVM Compiler Infrastructure
#
# This file was developed by the LLVM research group and is distributed under
# the University of Illinois Open Source License. See LICENSE.TXT for details.
# 
#===------------------------------------------------------------------------===#

LEVEL := ..
DIRS  := 

#
# Make Dejagnu the default for testing
#
all:: check-local

# Include other test rules
include Makefile.tests

#===------------------------------------------------------------------------===#
# DejaGNU testing support
#===------------------------------------------------------------------------===#

ifneq ($(RUNTEST),)
check-local:: site.exp
	( ulimit -t 600 ; ulimit -d 512000 ; \
	  PATH="$(ToolDir):$(LLVMToolDir):$(LLVM_SRC_ROOT)/test/Scripts:$(PATH)" \
	  $(RUNTEST) $(RUNTESTFLAGS) ;  \
	  ! grep FAIL testrun.log; ! grep -q ^FAIL testrun.log;  )
else
check-local:: site.exp
	@echo "*** dejagnu not found.  Make sure runtest is in your PATH, then reconfigure llvm."
endif

ifdef TESTONE
CLEANED_TESTONE := $(patsubst %/,%,$(TESTONE))
CLEANED_TESTONE := $(patsubst test/%,%,$(CLEANED_TESTONE))
SUBDIR := $(shell dirname $(CLEANED_TESTONE))
TESTPATH := $(PROJ_SRC_ROOT)/test/$(CLEANED_TESTONE)
check-one: site.exp $(TCLSH)
	$(Verb)( echo "source $(PROJ_OBJ_ROOT)/test/site.exp" ; \
	  echo "set subdir $(SUBDIR)" ; \
	  echo "proc pass  { msg } { puts \"PASS: \$$msg\" } "; \
	  echo "proc fail  { msg } { puts \"FAIL: \$$msg\" }" ; \
	  echo "proc xfail { msg } { puts \"XFAIL: \$$msg\" }" ; \
	  echo "proc xpass { msg } { puts \"XPASS: \$$msg\" }" ; \
	  echo "source $(PROJ_SRC_ROOT)/test/lib/llvm.exp" ; \
	  echo "RunLLVMTests $(TESTPATH)" ) | \
	( ulimit -t 600 ; ulimit -d 512000 ; \
	  PATH="$(ToolDir):$(LLVMToolDir):$(LLVM_SRC_ROOT)/test/Scripts:$(PATH)" \
	  $(TCLSH) )
endif

clean::
	$(RM) -rf `find $(PROJ_OBJ_ROOT)/test -name Output -type d -print`
	$(RM) -rf `find $(PROJ_OBJ_ROOT)/test -name '*~'`
	rm -f site.exp

site.exp: Makefile $(LEVEL)/Makefile.config
	@echo 'Making a new site.exp file...'
	@echo '## these variables are automatically generated by make ##' >site.tmp
	@echo '# Do not edit here.  If you wish to override these values' >>site.tmp
	@echo '# edit the last section' >>site.tmp
	@echo 'set target_triplet "$(TARGET_TRIPLE)"' >> site.tmp
	@echo 'set srcroot "$(PROJ_SRC_ROOT)"' >>site.tmp
	@echo 'set objroot "$(PROJ_OBJ_ROOT)"' >>site.tmp
	@echo 'set srcdir "$(PROJ_SRC_ROOT)/test"' >>site.tmp
	@echo 'set objdir "$(PROJ_OBJ_ROOT)/test"' >>site.tmp
	@echo 'set llvmlibdir "$(LLVMLibDir)"' >>site.tmp
	@echo 'set projlibdir "$(PROJ_OBJ_ROOT)/$(BuildMode)/lib"' >>site.tmp
	@echo 'set projbindir "$(PROJ_OBJ_ROOT)/$(BuildMode)/bin"' >>site.tmp
	@echo 'set llvmgcc "$(LLVMGCC) -g -O0 -I$(PROJ_SRC_ROOT)/include"' >> site.tmp
	@echo 'set llvmgxx "$(LLVMGXX) -g -O0 -I$(PROJ_SRC_ROOT)/include"' >> site.tmp
	@echo 'set ternruntime "-L$(PROJ_OBJ_ROOT)/$(BuildMode)/lib -lruntime -lcommon"' >>site.tmp
	@echo 'set ternannotlib "-I$(PROJ_SRC_ROOT)/include -L$(PROJ_SRC_ROOT)/dync_hook -lxtern-annot"' >>site.tmp
	@echo 'set ternbcruntime "$(PROJ_OBJ_ROOT)/$(BuildMode)/lib/runtime.bc $(PROJ_OBJ_ROOT)/$(BuildMode)/lib/common.bc"' >>site.tmp
	@echo 'set gcc "gcc -w"' >> site.tmp

	@echo 'set gxx "g++ -w"' >> site.tmp
	@echo 'set timeout 1' >> site.tmp
	@echo '## All variables above are generated by configure. Do Not Edit ## ' >>site.tmp
	@test ! -f site.exp || \
	sed '1,/^## All variables above are.*##/ d' site.exp >> site.tmp
	@-rm -f site.bak
	@test ! -f site.exp || mv site.exp site.bak
	@mv site.tmp site.exp
