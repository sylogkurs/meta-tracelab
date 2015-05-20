FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://perf-makefile-libunwind-arm-fix.patch"

do_configure_append() {
  # Enable lots of tracing
  echo "CONFIG_GENERIC_TRACER=y" >> ${S}/.config
  echo "CONFIG_FUNCTION_TRACER=y" >> ${S}/.config
  echo "CONFIG_IRQSOFF_TRACER=y" >> ${S}/.config
  echo "CONFIG_SCHED_TRACER=y" >> ${S}/.config
  echo "CONFIG_FTRACE_SYSCALLS=y" >> ${S}/.config
  echo "CONFIG_STACK_TRACER=y" >> ${S}/.config
  echo "CONFIG_DYNAMIC_FTRACE=y" >> ${S}/.config
  echo "CONFIG_FUNCTION_PROFILER=y" >> ${S}/.config
  echo "CONFIG_FTRACE_MCOUNT_RECORD=y" >> ${S}/.config
  echo "CONFIG_BINARY_PRINTF=y" >> ${S}/.config

  # Clean up config
  (
    cd ${S}
    yes '' | oe_runmake oldconfig
  )
}
