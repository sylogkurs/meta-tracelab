#RDEPENDS_${PN} += "audit libunwind elfutils"
DEPENDS += "audit libunwind elfutils"
EXTRA_OEMAKE += "NO_DWARF= V=1"

FILES_${PN} += "/usr/lib/traceevent "
FILES_${PN}-dbg += "/usr/lib/traceevent/plugins/.debug "
FILES_${PN}-python += "/usr/libexec/perf-core/scripts/python "
FILES_${PN}-perl += "/usr/libexec/perf-core/scripts/perl "
FILES_${PN}-archive += "/usr/libexec/perf-core/perf-archive "
FILES_${PN}-archive += "/usr/libexec/perf-core/perf-with-kcore "
FILES_${PN}-tests += "/usr/libexec/perf-core/tests "
