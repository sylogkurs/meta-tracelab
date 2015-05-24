require linux.inc

DESCRIPTION = "Linux kernel for beaglebone"
KERNEL_IMAGETYPE ?= "zImage"

COMPATIBLE_MACHINE = "(beaglebone)"

PV = "4.0.4"
# v4.0.4 tag
SRCREV_pn-${PN} = "8b660f48b5734edc272a008a34207cf1a249fcf7"

# The main PR is now using MACHINE_KERNEL_PR, for omap3 see conf/machine/include/omap3.inc
MACHINE_KERNEL_PR_append = "b"
PR = "${MACHINE_KERNEL_PR}"

SRC_URI += " \
  git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.0.y;protocol=git \
  "

S = "${WORKDIR}/git"

# Patch for perf
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-mainline:"
SRC_URI += "file://libtraceevent-remove-double-slash.patch \
    file://perf-libtraceevent-remove-double-slash.patch"

addtask copy_defconfig after do_patch before do_configure

do_copy_defconfig() {
  # Use omap2plus defconfig
  cp ${S}/arch/${ARCH}/configs/omap2plus_defconfig ${WORKDIR}/defconfig
}

do_configure_append() {

  # Enable Thumbee
  echo "CONFIG_ARM_THUMBEE=y" >> ${S}/.config

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

do_install_append() {
  # Fake contents for the kernel-devicetree-overlays package to satisfy
  # angstrom-packagegroup-boot
  touch ${D}/lib/firmware/fake.dtbo
}
